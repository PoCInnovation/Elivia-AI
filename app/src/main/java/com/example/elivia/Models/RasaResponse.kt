package com.example.elivia.Models

import com.beust.klaxon.*

private val klaxon = Klaxon()

data class RasaResponse (
        val text: String,
        val intent: Intent,
        val entities: List<Any?>,

        @Json(name = "intent_ranking")
        val intentRanking: List<Intent>,

        @Json(name = "response_selector")
        val responseSelector: ResponseSelector
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<RasaResponse>(json)
    }
}

data class Intent (
        val id: Long,
        val name: String,
        val confidence: Double
)

data class ResponseSelector (
        @Json(name = "all_retrieval_intents")
        val allRetrievalIntents: List<Any?>,

        val default: Default
)

data class Default (
        val response: Response,
        val ranking: List<Any?>
)

data class Response (
        val id: Any? = null,
        val responses: Any? = null,

        @Json(name = "response_templates")
        val responseTemplates: Any? = null,

        val confidence: Double,

        @Json(name = "intent_response_key")
        val intentResponseKey: Any? = null,

        @Json(name = "utter_action")
        val utterAction: String,

        @Json(name = "template_name")
        val templateName: String
)