package `mockito-extensions`.movietask3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movietask3.domain.repository.MovieRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ViewModelTest {

//    private val testDispatcher = StandardTestDispatcher()

    @Mock
    lateinit var repository: MovieRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
//    fun test_GetMovies() = runBlocking {
//        Mockito.`when`(repository.getMoviesApi("1.json"))
//            .thenReturn(emptyList<MovieResponse>()))
//        val sut = MovieViewModel(repository)
//        sut.getMoviesFromApi("!.json")
//        val result = sut.moviesApiData.getOrAwaitValue()
//        Assert.assertEquals(0, result.data!!.size)
//    }

    @After
    fun tearDown() {

    }


}