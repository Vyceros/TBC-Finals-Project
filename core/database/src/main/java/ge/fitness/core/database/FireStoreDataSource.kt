package ge.fitness.core.database

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import ge.fitness.core.database.entity.ArticleEntity
import ge.fitness.core.database.entity.ExerciseEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireStoreDataSource @Inject constructor(
    private val fireStore: FirebaseFirestore,
) {
    private val collection: CollectionReference = fireStore.collection("exercises")
    private val articles: CollectionReference = fireStore.collection("articles")

    suspend fun saveExercise(exercise: ExerciseEntity) {
        collection.document(exercise.id).set(exercise).await()
    }

    suspend fun saveExercises(exercises: List<ExerciseEntity>) {
        val batch = fireStore.batch()

        exercises.forEach { exercise ->
            val docRef = collection.document(exercise.id)
            batch.set(docRef, exercise)
        }
        batch.commit().await()
    }

    fun getExerciseById(id: String): Flow<ExerciseEntity?> = callbackFlow {
        val listener = collection.document(id).addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val exercise = snapshot.toObject<ExerciseEntity>()
                trySend(exercise)
            } else {
                trySend(null)
            }
        }

        awaitClose { listener.remove() }
    }

    fun getAllExercises(): Flow<List<ExerciseEntity>> = callbackFlow {
        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val exercises = snapshot.documents.mapNotNull { it.toObject<ExerciseEntity>() }
                trySend(exercises)
            } else {
                trySend(emptyList())
            }
        }

        awaitClose { listener.remove() }
    }

    fun getExercisesByBodyPart(bodyPart: String): Flow<List<ExerciseEntity>> = callbackFlow {
        val listener = collection
            .whereEqualTo("bodyPart", bodyPart)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val exercises = snapshot.documents.mapNotNull { it.toObject<ExerciseEntity>() }
                    trySend(exercises)
                } else {
                    trySend(emptyList())
                }
            }

        awaitClose { listener.remove() }
    }

    fun getExercisesByEquipment(equipment: String): Flow<List<ExerciseEntity>> = callbackFlow {
        val listener = collection
            .whereEqualTo("equipment", equipment)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val exercises = snapshot.documents.mapNotNull { it.toObject<ExerciseEntity>() }
                    trySend(exercises)
                } else {
                    trySend(emptyList())
                }
            }

        awaitClose { listener.remove() }
    }

    fun getExercisesByTarget(target: String): Flow<List<ExerciseEntity>> = callbackFlow {
        val listener = collection
            .whereEqualTo("target", target)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val exercises = snapshot.documents.mapNotNull { it.toObject<ExerciseEntity>() }
                    trySend(exercises)
                } else {
                    trySend(emptyList())
                }
            }

        awaitClose { listener.remove() }
    }

    suspend fun deleteExercise(id: String) {
        collection.document(id).delete().await()
    }

    suspend fun deleteAllExercises() {
        val snapshot = collection.get().await()
        val batch = fireStore.batch()

        snapshot.documents.forEach { document ->
            batch.delete(document.reference)
        }
        batch.commit().await()
    }

    suspend fun saveArticles(articles: List<ArticleEntity>) {
        val batch = fireStore.batch()

        articles.forEach { article ->
            val docRef = collection.document(article.url)
            batch.set(docRef, articles)
        }
        batch.commit().await()
    }

    fun getAllArticles(): Flow<List<ArticleEntity>> = callbackFlow {
        val listener = articles.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val articles = snapshot.documents.mapNotNull { it.toObject<ArticleEntity>() }
                trySend(articles)
            } else {
                trySend(emptyList())
            }
        }

        awaitClose { listener.remove() }
    }
}