package com.example.issue_tracker.common

import com.example.issue_tracker.model.Issue
import kotlinx.coroutines.flow.MutableStateFlow

fun <E> MutableStateFlow<MutableList<E>>.addElement(element: E) {
    if (element == null) {
        return
    }
    val tempMutableList = mutableListOf<E>()
    tempMutableList.addAll(this.value)
    tempMutableList.add(element)
    this.value = tempMutableList
}

fun <E> MutableStateFlow<MutableList<E>>.removeElement(element: E) {
    if (element == null) {
        return
    }
    val tempMutableList = mutableListOf<E>()
    tempMutableList.addAll(this.value)
    tempMutableList.remove(element)
    this.value = tempMutableList
}

fun <E> MutableStateFlow<MutableList<E>>.removeAllElement() {
    this.value = mutableListOf()
}

fun MutableStateFlow<MutableList<Issue>>.closeElement(issueId: Int) {
    val tempList = this.value.filter { issue ->
        issue.issueId != issueId
    }.map {
        it.copy()
    }
    this.value = tempList.toMutableList()
}