package kr.simppl.shared.common.enums

enum class Role(val code: String, val displayName: String) {
  ROLE_USER("U", "일반 사용자 권한"),
  ROLE_ADMIN("A", "관리자 권한"),
  ;

  companion object {
    fun from(authority: String): Role? {
      return entries.find { it.name == authority }
    }
  }
}
