package kr.simppl.shared.api.docs

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors

object RestDocsUtil {
  fun requestPreprocessor(): OperationRequestPreprocessor {
    return Preprocessors.preprocessRequest(
      Preprocessors.modifyUris().scheme("http").host("test.simppl.kr").removePort(),
      Preprocessors.prettyPrint(),
    )
  }

  fun responsePreprocessor(): OperationResponsePreprocessor {
    return Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
  }
}
