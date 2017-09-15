/*******************************************************************************
 * Copyright 2017 Yuran Zhang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.zyr.library;

import com.zyr.library.model.Country;
import com.zyr.library.model.Person;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the {@linkplain FastJsonConverterFactory}.
 */
public class FastJsonConverterFactoryTest {
    private Service service;

    @Rule
    public final MockWebServer mockWebServer = new MockWebServer();

    private final Dispatcher dispatcher = new Dispatcher() {
        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            switch (request.getPath()) {
                case "/person/5":
                    return new MockResponse().setResponseCode(200)
                            .setBody("{\"name\": \"A\",\"age\": 11}");
                case "/country":
                    return new MockResponse().setResponseCode(200)
                            .setBody("{\"name\":\"中国\",\"" +
                                    "province\":[{\"name\":\"黑龙江\",\"cities\":{\"city\":[\"哈尔滨\",\"大庆\"]}}," +
                                    "{\"name\":\"广东\",\"cities\":{\"city\":[\"广州\",\"深圳\",\"珠海\"]}}," +
                                    "{\"name\":\"台湾\",\"cities\":{\"city\":[\"台北\",\"高雄\"]}}," +
                                    "{\"name\":\"新疆\",\"cities\":{\"city\":[\"乌鲁木齐\"]}}]}");
                default:
                    return new MockResponse().setResponseCode(404);
            }
        }
    };

    @Before
    public void setup() {
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(FastJsonConverterFactory.create())
                .build()
                .create(Service.class);
    }

    @Test
    public void testGET() throws Exception {
        mockWebServer.enqueue(new MockResponse().setBody("{\"name\": \"ZYRzyr\",\"age\": 100}"));

        Call<Person> call = service.fetchPerson();
        Person person = call.execute().body();

        assertNotNull(person);
        assertEquals("ZYRzyr", person.getName());
        assertEquals(100, person.getAge());
        assertEquals("GET", mockWebServer.takeRequest().getMethod());
    }

    @Test
    public void testGET_WithParam() throws Exception {
        mockWebServer.setDispatcher(dispatcher);
        Call<Person> call = service.fetchPersonById(5);
        Person person = call.execute().body();

        assertNotNull(person);
        assertEquals("A", person.getName());
        assertEquals(11, person.getAge());
        assertEquals("GET", mockWebServer.takeRequest().getMethod());
    }

    @Test
    public void testGET_List() throws Exception {
        mockWebServer.setDispatcher(dispatcher);
        Call<Country> call = service.fetchCountry();
        Country country = call.execute().body();

        assertNotNull(country);
        assertEquals("中国", country.getName());
        assertEquals("台湾", country.getProvince().get(2).getName());
        assertEquals("乌鲁木齐", country.getProvince().get(3).getCities().getCity().get(0));
        assertEquals("GET", mockWebServer.takeRequest().getMethod());
    }

    @Test
    public void testPOST() throws Exception {
        mockWebServer.enqueue(new MockResponse().setBody("{\"name\": \"Tom\",\"age\": 100}"));
        Call<Person> call = service.modifyPerson("Tom");
        Person person = call.execute().body();

        assertNotNull(person);
        assertEquals("Tom", person.getName());
        assertEquals(100, person.getAge());
        assertEquals("POST", mockWebServer.takeRequest().getMethod());
    }

    private interface Service {
        @GET("/")
        Call<Person> fetchPerson();

        @GET("person/{id}")
        Call<Person> fetchPersonById(@Path("id") int id);

        @GET("country")
        Call<Country> fetchCountry();

        @FormUrlEncoded
        @POST("modify")
        Call<Person> modifyPerson(@Field("name") String name);
    }
}
