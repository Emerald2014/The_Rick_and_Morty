package ru.kudesnik.therickandmorty.model.entities.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ModelRepo {
    val api: ModelAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilder())
            .build()

        adapter.create(ModelAPI::class.java)
    }
}