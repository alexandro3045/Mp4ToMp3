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
package ufla.projects.mp4tomp3Test.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

/**
 * Test class for the service {@link GreetingApplicationService}.
 */
class GreetingApplicationServiceTest {

  private GreetingApplicationService service;

  private GreetingRepository repository;

  @BeforeEach
  void setUp() {
    repository = Mockito.mock(GreetingRepository.class);
    service = new GreetingApplicationService("Hello", repository);
  }

  @Test
  void getGreeting() {
    assertThat(service.getGreeting()).isEqualTo("Hello");
  }

  @Test
  void getMessage() {
    assertThat(service.getGreet("World")).isEqualTo("Hello World!");
  }

  @Test
  void getResponse() {
    Mockito.doReturn(Optional.of(new Greeting("Marco", "Polo"))).when(repository).findBySalutation(Mockito.anyString());
    assertThat(service.getResponse("Marco")).isInstanceOf(Optional.class);
  }

  @Test
  void updateGreeting() {
    service.updateGreeting("Hola");
    assertThat(service.getGreeting()).isEqualTo("Hola");
  }
}