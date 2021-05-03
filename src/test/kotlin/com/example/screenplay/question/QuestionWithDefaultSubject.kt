package com.example.screenplay.question

import net.serenitybdd.screenplay.Question

abstract class QuestionWithDefaultSubject<T> : Question<T> {
  override fun getSubject(): String {
    return this::class.simpleName!!.replace("([a-z])([A-Z])".toRegex(), "$1 $2")
  }

  override fun toString(): String {
    return subject
  }
}