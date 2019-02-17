package com.eamh.garmentreviews.di

import com.eamh.garmentreviews.repository.InMemoryRepository
import com.eamh.garmentreviews.repository.Repository
import org.koin.dsl.module.module

val DependencyModule =
        module {
            single<Repository> {
                InMemoryRepository()
            }
        }