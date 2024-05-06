package kr.jaime.keycloak.service

data class Bug(
  val id: Long? = null,
  val submitter: String,
  val headline: String,
  val description: String,
  val project: String,
  val severity: Severity = Severity.LOW,
  val state: State = State.OPEN,
) {
  enum class Severity {
    LOW, MINOR, MAJOR, CRITICAL,
    ;
  }

  enum class State {
    OPEN, CLOSED,
    ;
  }

  companion object {
    fun emptyBug(submitter: String) =
      Bug(
        submitter = submitter,
        headline = "",
        description = "",
        project = "",
      )
  }
}
