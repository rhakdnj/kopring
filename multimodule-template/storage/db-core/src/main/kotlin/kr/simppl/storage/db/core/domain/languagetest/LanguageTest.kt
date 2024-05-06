package kr.simppl.storage.db.core.domain.languagetest

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import kr.simppl.storage.db.core.domain.Base
import kr.simppl.storage.db.core.domain.user.User
import java.time.LocalDate

@Entity
@Table(name = "Language_Test")
class LanguageTest(
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(name = "exam_date", nullable = false)
  val examDate: LocalDate,

  @Column(name = "transcript", nullable = false, columnDefinition = "TEXT")
  var transcript: String,

  @Column(name = "is_check", nullable = false, columnDefinition = "BIT")
  var isChecked: Boolean = false,

  @Column(name = "publishing_organization", nullable = false, length = 45)
  var publisher: String,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  val user: User,

  @OneToOne(mappedBy = "languageTest", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
  val dele: LanguageDeleEntity? = null,

  @OneToOne(mappedBy = "languageTest", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
  val delf: LanguageDelfEntity? = null,

  @OneToOne(mappedBy = "languageTest", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
  val teps: LanguageTepsEntity? = null,

  @OneToOne(mappedBy = "languageTest", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
  val toefl: LanguageToeflEntity? = null,

  @OneToOne(mappedBy = "languageTest", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
  val toeic: LanguageToeicEntity? = null,

  @OneToOne(mappedBy = "languageTest", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
  val ielts: LanguageIeltsEntity? = null,

  @OneToOne(mappedBy = "languageTest", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
  val hsk: LanguageHskEntity? = null,

  @OneToOne(mappedBy = "languageTest", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
  val etc: LanguageEtc? = null,
) : Base()
