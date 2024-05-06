package kr.simppl.api.domain.activityaward

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "활동 및 수상")
@RestController
@RequestMapping("/activity-awards")
class ActivityAwardController {
  @Operation(summary = "활동 및 수상 생성")
  @PostMapping
  fun createActivityAward(): String {
    return "createActivityAward"
  }
}
