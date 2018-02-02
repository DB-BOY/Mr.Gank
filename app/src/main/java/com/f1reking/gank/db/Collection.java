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

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: F1ReKing
 * @date: 2018/2/1 20:42
 * @desc:
 */
@Entity public class Collection {

  @Id private String id;
  private String desc;
  private String publishedAt;
  private String type;
  private String url;
  private String who;

  @Generated(hash = 245714445)
  public Collection(String id, String desc, String publishedAt, String type, String url,
      String who) {
    this.id = id;
    this.desc = desc;
    this.publishedAt = publishedAt;
    this.type = type;
    this.url = url;
    this.who = who;
  }

  @Generated(hash = 1149123052)
  public Collection() {
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDesc() {
    return this.desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getPublishedAt() {
    return this.publishedAt;
  }

  public void setPublishedAt(String publishedAt) {
    this.publishedAt = publishedAt;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getWho() {
    return this.who;
  }

  public void setWho(String who) {
    this.who = who;
  }
}
