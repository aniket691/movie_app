package `mockito-extensions`.movietask3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietask3.data.remote.MovieApi
import com.example.movietask3.domain.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiTest {

    //    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var mockWebServer: MockWebServer
    lateinit var movieApi: MovieApi

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        movieApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url(("/")))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MovieApi::class.java)
    }

    @Test
//    fun test_GetMovies() = runBlocking {
//        val mockResponse = MockResponse()
//        mockResponse.setBody("[]")
//        mockWebServer.enqueue(mockResponse)
//        val response = movieApi.getMovies("1.json")
//        mockWebServer.takeRequest()
//        Assert.assertEquals(true, response.body()?.movieList!!.isEmpty())
//    }

    @After
    fun tearDown() {

    }


}