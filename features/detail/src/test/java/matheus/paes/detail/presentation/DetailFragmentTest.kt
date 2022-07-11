package matheus.paes.detail.presentation

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import matheus.paes.detail.R
import matheus.paes.models.RepoEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(
    instrumentedPackages = [
        // required to access final members on androidx.loader.content.ModernAsyncTask
        "androidx.loader.content"
    ]
)
@RunWith(AndroidJUnit4::class)
internal class DetailFragmentTest {
    lateinit var scenario: FragmentScenario<DetailFragment>
    lateinit var repoEntity: RepoEntity

    @Before
    fun setUp() {
        repoEntity = RepoEntity(
            1111,
            "Modularization",
            "Modularization Sample by Matheus",
            "Este é um repositório de testes",
            "",
            12,
            3,
            "PT-BR",
            "Matheus Paes Crescêncio",
            ""
        )
        val args = Bundle().apply {
            putParcelable(
                DetailFragment.REPO_DETAIL,
                repoEntity
            )
        }
        scenario = FragmentScenario.launchInContainer(DetailFragment::class.java, args)
    }

    @Test
    fun `Check repo author name presented`() {
        onView(withId(R.id.txtAuthorName)).check(matches(withText(repoEntity.ownerName)))
    }

    @Test
    fun `Check repo name presented`() {
        onView(withId(R.id.txtRepoName)).check(matches(withText(repoEntity.fullName)))
    }

    @Test
    fun `Check repo description presented`() {
        onView(withId(R.id.txtRepoDesc)).check(matches(withText(repoEntity.description)))
    }

    @Test
    fun `Check repo stars presented`() {
        onView(withId(R.id.txtStarsCount)).check(matches(withText(repoEntity.stars.toString())))
    }

    @Test
    fun `Check repo forks presented`() {
        onView(withId(R.id.txtForksCount)).check(matches(withText(repoEntity.forks.toString())))
    }
}