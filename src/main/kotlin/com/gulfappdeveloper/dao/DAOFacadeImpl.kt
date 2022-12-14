package com.gulfappdeveloper.dao

import com.gulfappdeveloper.dao.DatabaseFactory.dbQuery
import com.gulfappdeveloper.models.Article
import com.gulfappdeveloper.models.Articles
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToArticle(row: ResultRow) = Article(
        id = row[Articles.id],
        title = row[Articles.title],
        body = row[Articles.body]
    )

    override suspend fun allArticles(): List<Article> = dbQuery {
        Articles.selectAll().map(::resultRowToArticle)
    }

    override suspend fun article(id: Int): Article? {
        return dbQuery {
            Articles.select {
                Articles.id eq id
            }.map {
                resultRowToArticle(it)
            }.singleOrNull()
        }
    }

    override suspend fun addNewArticle(title: String, body: String): Article? {
        return dbQuery {
            val insertStatement = Articles.insert {
                it[Articles.title] = title
                it[Articles.body] = body
            }
            insertStatement.resultedValues?.singleOrNull()?.let { resultRow ->
                resultRowToArticle(resultRow)
            }
        }
    }

    override suspend fun editArticle(id: Int, title: String, body: String): Boolean {
        return dbQuery {
            Articles.update({
                Articles.id eq id
            }) {
                it[Articles.title] = title
                it[Articles.body] = body
            }
        } > 0
    }

    override suspend fun deleteArticle(id: Int): Boolean {
        return dbQuery {
            Articles.deleteWhere {
                Articles.id eq id
            } > 0
        }
    }

}