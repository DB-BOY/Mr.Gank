/*
 *  Copyright (c) 2018 F1ReKing
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.f1reking.gank.db;

import android.database.Cursor;
import android.text.TextUtils;
import com.f1reking.gank.db.dao.DaoMaster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

/**
 * Created by F1ReKing on 2016/5/20.
 * greenDao升级辅助类
 */

public class MigrationHelper {

  private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION =
      "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";
  private static MigrationHelper instance;

  public static MigrationHelper getInstance() {
    if (instance == null) {
      instance = new MigrationHelper();
    }
    return instance;
  }

  public void migrate(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
    generateTempTables(db, daoClasses);
    DaoMaster.dropAllTables(db, true);
    DaoMaster.createAllTables(db, false);
    restoreData(db, daoClasses);
  }

  private void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
    for (int i = 0; i < daoClasses.length; i++) {
      DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);

      String divider = "";
      String tableName = daoConfig.tablename;
      String tempTableName = daoConfig.tablename.concat("_TEMP");
      ArrayList<String> properties = new ArrayList<>();

      StringBuilder createTableStringBuilder = new StringBuilder();

      createTableStringBuilder.append("CREATE TABLE ").append(tempTableName).append(" (");

      for (int j = 0; j < daoConfig.properties.length; j++) {
        String columnName = daoConfig.properties[j].columnName;

        if (getColumns(db, tableName).contains(columnName)) {
          properties.add(columnName);
          String type = null;
          try {
            type = getTypeByClass(daoConfig.properties[j].type);
          } catch (Exception exception) {
            exception.printStackTrace();
          }
          createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);
          if (daoConfig.properties[j].primaryKey) {
            createTableStringBuilder.append(" PRIMARY KEY");
          }
          divider = ",";
        }
      }
      createTableStringBuilder.append(");");

      db.execSQL(createTableStringBuilder.toString());

      StringBuilder insertTableStringBuilder = new StringBuilder();

      insertTableStringBuilder.append("INSERT INTO ").append(tempTableName).append(" (");
      insertTableStringBuilder.append(TextUtils.join(",", properties));
      insertTableStringBuilder.append(") SELECT ");
      insertTableStringBuilder.append(TextUtils.join(",", properties));
      insertTableStringBuilder.append(" FROM ").append(tableName).append(";");

      db.execSQL(insertTableStringBuilder.toString());
    }
  }

  private void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
    for (int i = 0; i < daoClasses.length; i++) {
      DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);

      String tableName = daoConfig.tablename;
      String tempTableName = daoConfig.tablename.concat("_TEMP");
      List<String> properties = new ArrayList();

      for (int j = 0; j < daoConfig.properties.length; j++) {
        String columnName = daoConfig.properties[j].columnName;

        if (getColumns(db, tempTableName).contains(columnName)) {
          properties.add(columnName);
        }
      }

      StringBuilder insertTableStringBuilder = new StringBuilder();

      insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
      insertTableStringBuilder.append(TextUtils.join(",", properties));
      insertTableStringBuilder.append(") SELECT ");
      insertTableStringBuilder.append(TextUtils.join(",", properties));
      insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");

      StringBuilder dropTableStringBuilder = new StringBuilder();

      dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);

      db.execSQL(insertTableStringBuilder.toString());
      db.execSQL(dropTableStringBuilder.toString());
    }
  }

  private String getTypeByClass(Class<?> type) throws Exception {
    if (type.equals(String.class)) {
      return "URL";
    }
    if (type.equals(Long.class) || type.equals(Integer.class) || type.equals(long.class)) {
      return "INTEGER";
    }
    if (type.equals(Boolean.class)) {
      return "BOOLEAN";
    }

    throw new Exception(
        CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - ClassEntity: ").concat(type.toString()));
  }

  private static List<String> getColumns(Database db, String tableName) {
    List<String> columns = new ArrayList<>();
    Cursor cursor = null;
    try {
      cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
      if (cursor != null) {
        columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (cursor != null) cursor.close();
    }
    return columns;
  }
}
