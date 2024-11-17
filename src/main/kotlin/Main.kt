package com.example

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

data class PeopleInfo(
    val name: String,
    val age: Int
)

fun main() {

    val resultJsonString =
        """{"code":"200","message":"success","data":[{"name":"san","age":"17"},{"name":"si","age":"18"}]}"""
    println(resultJsonString)
    val result = objectMapper.readTree(resultJsonString)
    println(result)

    val code = result.get("code").asInt()
    val message = result.get("message").asText()
    val data: MutableList<PeopleInfo> = result.get("data").getArray()
    println(code)
    println(message)
    println(data)

    val resultObjectJsonString = """{"name":"wang","age":"19"}"""
    val people: PeopleInfo = resultObjectJsonString.fromJson()
    println(people)
}

private val objectMapper =
    ObjectMapper().registerModule(KotlinModule.Builder().build())

private inline fun <reified T> JsonNode.getArray(): MutableList<T> =
    objectMapper.convertValue(this, object : TypeReference<MutableList<T>>() {})

private inline fun <reified T> String.fromJson(): T =
    objectMapper.readValue(this, object : TypeReference<T>() {})


