package jp.mknod.sample.cognito

import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery

import com.amplifyframework.kotlin.core.Amplify
//import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TodoApi {

    val TAG = "UserData"

    suspend fun get(name: String):List<Todo>  = withContext(Dispatchers.IO) {

        var datas = listOf<Todo>()
        try {
            var response = Amplify.API
                .query(ModelQuery.list(Todo::class.java, Todo.NAME.contains(name)))

            datas = response.data.items as List<Todo>
            response.data.items.forEach{ todo -> Log.i("MyAmplifyApp", todo.name) }

        } catch (error: ApiException) {
            Log.e("MyAmplifyApp", "Query failed", error)
        }
        return@withContext datas
    }

    suspend fun get():List<Todo>  = withContext(Dispatchers.IO) {

        var datas = listOf<Todo>()
        try {
            var response = Amplify.API.query( ModelQuery.list(Todo::class.java) )

            datas = response.data.items as List<Todo>
            response.data.items.forEach{ todo -> Log.i("MyAmplifyApp", todo.name) }

        } catch (error: ApiException) {
            Log.e("MyAmplifyApp", "Query failed", error)
        }
        return@withContext datas
    }

    suspend fun add( name: String,desc: String, image: String): Boolean = withContext(Dispatchers.IO) {

        var ret = false
        val data = Todo.builder()
            .name(name)
            .description(desc)
            .build()

        try {
            val response = Amplify.API.mutate(ModelMutation.create(data))
            Log.i("MyAmplifyApp", "Added Todo with id: $response")
            ret = true
        } catch (error: ApiException) {
            Log.e("MyAmplifyApp", "Create failed", error)
        }
        return@withContext ret
    }

    suspend fun update( todo:Todo ): Boolean = withContext(Dispatchers.IO) {

        var ret = false
        try {
            val response = Amplify.API.mutate(ModelMutation.update(todo))
            Log.i("MyAmplifyApp", "Added Todo with id: $response")
            ret = true
        } catch (error: ApiException) {
            Log.e("MyAmplifyApp", "Create failed", error)
        }
        return@withContext ret
    }

    suspend fun delete( todo:Todo ): Boolean = withContext(Dispatchers.IO) {

        var ret = false
        try {
            val response = Amplify.API.mutate(ModelMutation.delete(todo))
            Log.i("MyAmplifyApp", "Added Todo with id: $response")
            ret = true
        } catch (error: ApiException) {
            Log.e("MyAmplifyApp", "Create failed", error)
        }
        return@withContext ret
    }

}