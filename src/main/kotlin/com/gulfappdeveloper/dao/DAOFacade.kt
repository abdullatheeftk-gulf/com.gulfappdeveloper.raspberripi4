package com.gulfappdeveloper.dao

import com.gulfappdeveloper.models.Article

interface DAOFacade {
    suspend fun allArticles(): List<Article>
    suspend fun article(id:Int):Article?
    suspend fun addNewArticle(title:String,body:String):Article?
    suspend fun editArticle(id: Int,title: String,body: String):Boolean
    suspend fun deleteArticle(id:Int):Boolean

    suspend fun deleteAllArticles():Boolean

    suspend fun searchOneArticle(value:String):List<Article>
}