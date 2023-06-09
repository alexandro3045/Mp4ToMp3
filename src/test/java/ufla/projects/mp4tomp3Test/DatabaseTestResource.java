/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ufla.projects.mp4tomp3Test;

import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;
import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

/**
 * Quarkus test resource for the database.
 */
public class DatabaseTestResource implements QuarkusTestResourceLifecycleManager {

  private static final PostgreSQLContainer<?> DATABASE = new PostgreSQLContainer<>("postgres:12-alpine")
      .withDatabaseName("greeting-test")
      .withUsername("postgres")
      .withPassword("postgres");

  @Override
  public Map<String, String> start() {
    DATABASE.start();
    return Collections.singletonMap("quarkus.datasource.jdbc.url", DATABASE.getJdbcUrl());
  }

  @Override
  public void stop() {
    DATABASE.stop();
  }
}
