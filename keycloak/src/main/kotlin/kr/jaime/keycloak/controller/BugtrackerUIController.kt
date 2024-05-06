package kr.jaime.keycloak.controller

import jakarta.validation.constraints.NotNull
import kr.jaime.keycloak.service.Bug
import kr.jaime.keycloak.service.BugTrackerService
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class BugTrackerUIController(
  private val trackerServ: BugTrackerService
) {

  @GetMapping("/")
  fun slash(): String {
    return "redirect:/bugtracker/ui"
  }

  @GetMapping("/bugtracker/ui")
  fun home(
    token: OAuth2AuthenticationToken,
    @RegisteredOAuth2AuthorizedClient client: OAuth2AuthorizedClient
  ): ModelAndView {
    val principal = token.principal as OidcUser

    val model = generateDefaultModel(token)

    // Add accesstoken, refreshtoken and idtoken
    model.viewName = "home"
    model.addObject(
      "accesstoken",
      prettyBody(client.accessToken.tokenValue)
    )
    model.addObject(
      "refreshtoken",
      prettyBody(client.refreshToken!!.tokenValue)
    )
    model.addObject(
      "idtoken",
      prettyBody(principal.idToken.tokenValue)
    )
    return model
  }

  @GetMapping("/bugtracker/ui/show-bugs")
  fun showBugs(token: OAuth2AuthenticationToken): ModelAndView {
    val model = generateDefaultModel(token)

    model.viewName = "bugs"
    model.addObject("bugs", trackerServ.findAllBugs())
    return model
  }

  @GetMapping("/bugtracker/ui/show-create-form")
  fun showCreateForm(token: OAuth2AuthenticationToken): ModelAndView {
    val cfg = trackerServ.getConfiguration()

    val model = generateDefaultModel(token)

    model.viewName = "bug-form"
    model.addObject("bug", Bug.emptyBug(token.name))
    model.addObject("projects", cfg.projects)
    return model
  }

  @PostMapping("/bugtracker/ui/save-bug")
  fun saveBug(
    token: OAuth2AuthenticationToken?,
    @ModelAttribute bug: Bug
  ): String {
    if (bug.id == null) trackerServ.createBug(bug)
    else trackerServ.updateBug(bug)

    return "redirect:/bugtracker/ui/show-bugs"
  }

  @GetMapping("/bugtracker/ui/show-update-form")
  fun showUpdateForm(
    token: OAuth2AuthenticationToken,
    @RequestParam bugId: @NotNull Long?
  ): ModelAndView {
    // Get the bug

    val bug: Bug = trackerServ.getBug(bugId)

    val cfg = trackerServ.getConfiguration()

    val model = generateDefaultModel(token)
    model.viewName = "bug-form"
    model.addObject("bug", bug)
    model.addObject("projects", cfg.projects)
    return model
  }

  @GetMapping("/bugtracker/ui/delete-bug")
  fun deleteBug(
    token: OAuth2AuthenticationToken?,
    @RequestParam bugId: @NotNull Long?
  ): String {
    trackerServ.deleteBug(bugId)
    return "redirect:/bugtracker/ui/show-bugs"
  }

  @GetMapping("/bugtracker/ui/admin/show-edit-config")
  fun showEditConfiguration(token: OAuth2AuthenticationToken): ModelAndView {
    val cfg = trackerServ.getConfiguration()

    val model = generateDefaultModel(token)
    model.viewName = "config-form"
    model.addObject("configuration", cfg)
    return model
  }

  @PostMapping("/bugtracker/ui/admin/add-project")
  fun addProject(
    token: OAuth2AuthenticationToken?,
    @RequestParam project: String
  ): String {
    trackerServ.addProject(project)
    return "redirect:/bugtracker/ui/admin/show-edit-config"
  }

  @GetMapping("/bugtracker/ui/admin/remove-project")
  fun removeProject(
    token: OAuth2AuthenticationToken?,
    @RequestParam project: String,
  ): String {
    trackerServ.removeProject(project)
    return "redirect:/bugtracker/ui/admin/show-edit-config"
  }


  /*
     * Sets some basic user information. The call can add more properties
     * to it before passing to the view file.
     */
  private fun generateDefaultModel(token: OAuth2AuthenticationToken): ModelAndView {
    val principal = token.principal as OidcUser

    val model = ModelAndView()
    model.addObject("user", principal)
    return model
  }
}
