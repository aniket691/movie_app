package `mockito-extensions`.movietask3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietask3.common.Resource
import com.example.movietask3.data.remote.MovieApi
import com.example.movietask3.data.remote.dto.MovieResponse
import com.example.movietask3.domain.db.MovieDao
import com.example.movietask3.domain.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MovieRepositoryTest {
    //    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var movieApi: MovieApi
    lateinit var movieDao: MovieDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
//    fun test_GetMovies(): Unit = runBlocking {
//        Mockito.`when`(movieApi.getMovies("5.json"))
//            .thenReturn(Response.success(MovieResponse(emptyList())))
//        val sut = MovieRepository(movieDao, movieApi)
//        val result = sut.getMoviesApi("5.json")
//    }

    @After
    fun tearDown() {

    }
}