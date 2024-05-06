package kr.jaime.keycloak.service

import jakarta.annotation.PostConstruct
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service
import java.util.*
import java.util.List.copyOf
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.IntStream

@Service
class BugTrackerService {
  private val trackerConfig: AtomicReference<BugTrackerConfig> = AtomicReference(
    BugTrackerConfig(projects = INITIAL_APPS)
  )

  private val idGenerator = AtomicLong()
  private val bugs = Collections.synchronizedList(mutableListOf<Bug>())

  fun createBug(bug: Bug): Bug {
    // extract the user of the Application
    val token = SecurityContextHolder.getContext().authentication as OAuth2AuthenticationToken
    val principal = token.principal as OidcUser

    // create a new Bug
    val id = idGenerator.getAndIncrement()
    val clonedBug = Bug(
      id = id,
      submitter = principal.preferredUsername,
      headline = bug.headline,
      description = bug.description,
      project = bug.project,
      severity = bug.severity,
      state = bug.state
    )
    bugs.add(clonedBug)
    return clonedBug
  }

  fun updateBug(bug: Bug): Bug {
    val index = IntStream.range(0, bugs.size)
      .filter { i: Int -> bugs[i].id == bug.id }
      .findFirst()
      .orElseThrow()

    bugs[index] = bug
    return bug
  }

  fun findAllBugs(): List<Bug> {
    return copyOf(this.bugs)
  }

  fun deleteBug(bugId: Long?): Boolean {
    return bugs.removeIf { bug: Bug -> bug.id == bugId }
  }

  fun getBug(id: Long?): Bug {
    return bugs.stream()
      .filter { b: Bug -> b.id == id }
      .findFirst()
      .orElseThrow()
  }

  fun getConfiguration(): BugTrackerConfig {
    return trackerConfig.get()
  }

  fun addProject(newProject: String) {
    val configuration = trackerConfig.get()
    val projects: List<String> = configuration.projects

    val newApps = mutableListOf<String>()
    newApps.add(newProject)
    newApps.addAll(projects)
    trackerConfig.set(BugTrackerConfig(newApps))
  }

  fun removeProject(project: String) {
    val configuration = trackerConfig.get()
    val applications: List<String> = configuration.projects
    val newAppList = applications.stream().filter { a: String -> a != project }.toList()
    trackerConfig.set(BugTrackerConfig(newAppList))
  }


  @PostConstruct
  fun init() {
    val id = idGenerator.getAndIncrement()
    bugs.add(Bug(
      id = id,
      submitter = "jmchung",
      headline = "Initial Bug",
      description = "This is the initial bug",
      severity = Bug.Severity.MAJOR,
      project = "App1",
    ))

    val id2 = idGenerator.getAndIncrement()
    bugs.add(Bug(
      id = id2,
      submitter = "jmchung",
      headline = "Initial Bug 2",
      description = "This is the initial bug 2",
      project = "App2",
      severity = Bug.Severity.MAJOR,
      state = Bug.State.CLOSED,
      ))

    val id3 = idGenerator.getAndIncrement()
    bugs.add(Bug(
      id = id3,
      submitter = "jmchung",
      headline = "Initial Bug 3",
      description = "This is the initial bug 3",
      project = "App3",
      severity = Bug.Severity.CRITICAL,
      state = Bug.State.CLOSED,
    ))

    val id4 = idGenerator.getAndIncrement()
    bugs.add(Bug(
      id = id4,
      submitter = "jmchung",
      headline = "Initial Bug 4",
      description = "This is the initial bug 4",
      project = "App4",
      severity = Bug.Severity.MINOR,
      state = Bug.State.CLOSED,
    ))
  }

  companion object {
    private val INITIAL_APPS = listOf("App1", "App2", "App3", "App4", "App5")
  }
}
