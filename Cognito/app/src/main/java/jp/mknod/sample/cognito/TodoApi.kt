package jp.mknod.sample.cognito

import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.datastore.DataStoreException

import com.amplifyframework.kotlin.core.Amplify
//import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

object TodoApi {

    val TAG = "UserData"

    suspend fun get(name: String):List<Todo>  = withContext(Dispatchers.IO) {

        var datas = listOf<Todo>()
        try {
            var response = Amplify.API
                .query(ModelQuery.list(Todo::class.java, Todo.NAME.contains(name)))

            datas = response.data.items as List<Todo>
//            response.data.items.forEach{ todo -> Log.i("MyAmplifyApp", todo.name) }

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
    suspend fun clear(): Boolean = withContext(Dispatchers.IO) {

        Amplify.DataStore.clear()
        return@withContext true
    }

    suspend fun addByDatasync(name: String, desc: String): Boolean = withContext(Dispatchers.IO) {

        var ret = false
        val data = Todo.builder()
            .name(name)
            .description(desc)
            .build()

        try {
            Amplify.DataStore.save(data)
            Log.i("MyAmplifyApp", "Saved a new post successfully")
        } catch (error: DataStoreException) {
            Log.e("MyAmplifyApp", "Error saving post", error)
        }
        return@withContext ret
    }

//    fun sync_start() {
//
//        val sync_todo: Flow<List<Todo>> = flow {
//            while (true) {
//                Amplify.DataStore.query(Todo::class)
//                    .catch{
//                        Log.e(TAG, "Update failed", it)
//                    }
//                    .collect {
//                            fetchTodo->
//                    }
//
//                val latestNews = newsApi.fetchLatestNews()
//                emit(latestNews)
//                delay(5000)
//            }
//        }
//    }
//    interface NewsApi {
//        suspend fun fetchTodo(): List<Todo>
//    }

    suspend fun getByDatasync( datas:ArrayList<Todo> ):Boolean = withContext(Dispatchers.IO) {

        var ret:Boolean = false
        datas.clear()

        Amplify.DataStore.query(Todo::class)
            .catch{
                Log.e(TAG, "Update failed", it)
            }
            .collect {
                ret = true
                datas.add(it)
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