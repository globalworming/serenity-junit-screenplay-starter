package com.example.screenplay

import com.example.screenplay.data.requestBody.CreateCharacterRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import net.serenitybdd.screenplay.Actor
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

class RequestBodyFactory {


  companion object {
    val template = Files.lines(Path.of(this::class.java.getResource("createCharacter.json").toURI())).collect(Collectors.joining())

    fun body(actor: Actor) : String = String.format(template, actor.name)

    public fun <T : Actor> buildCreatequestBody(actor: T) = body(actor)

    public fun <T : Actor> buildCreatequestBody2(actor: T) = body2(actor)

    private fun body2(actor: Actor): String =
        jacksonObjectMapper().writeValueAsString(CreateCharacterRequest(title = actor.name + "assa"))
  }
}