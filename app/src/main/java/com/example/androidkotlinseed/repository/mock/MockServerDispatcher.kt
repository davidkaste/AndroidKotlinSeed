package com.example.androidkotlinseed.repository.mock

import com.example.androidkotlinseed.api.MarvelApi
import com.example.androidkotlinseed.repository.DataMock.Companion.mockHeroesJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection.HTTP_OK

class MockServerDispatcher {
    /**
     * Return ok response from mock server
     */
    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when {
                request.path == MarvelApi.GET_HEROES_PATH -> MockResponse().setResponseCode(HTTP_OK).setBody(mockHeroesJson)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }
}