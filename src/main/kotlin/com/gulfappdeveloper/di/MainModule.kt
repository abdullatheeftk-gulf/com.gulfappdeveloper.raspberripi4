package com.gulfappdeveloper.di

import com.gulfappdeveloper.dao.DAOFacade
import com.gulfappdeveloper.dao.DAOFacadeImpl
import com.gulfappdeveloper.dao.DatabaseFactory
import org.koin.dsl.module


val mainModule = module {
   /* single {
        DatabaseFactory.init()
    }*/
    single<DAOFacade> {
        DAOFacadeImpl()
    }
}