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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.f1reking.gank.db.dao.CollectionDao;
import com.f1reking.gank.db.dao.DaoMaster;
import org.greenrobot.greendao.database.Database;

/**
 * Created by F1ReKing on 2016/4/25.
 */
public class DBOpenHelper extends DaoMaster.DevOpenHelper {

  public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
    super(context, name, factory);
  }

  @Override
  public void onUpgrade(Database db, int oldVersion, int newVersion) {
    Log.i("greenDAO", "Upgrading schema from version "
        + oldVersion
        + " to "
        + newVersion
        + " by migrating all tables data");

    MigrationHelper.getInstance().migrate(db, CollectionDao.class);
  }
}
