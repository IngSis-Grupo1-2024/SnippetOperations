package manager.testCase.controller

import manager.common.rest.BasicRest.Companion.getUserId
import manager.common.rest.dto.Output
import manager.common.rest.exception.BadReqException
import manager.common.rest.exception.ErrorOutput
import manager.common.rest.exception.NotFoundException
import manager.testCase.service.TestCaseService
import manager.testCase.model.input.TestCaseInput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test_case")
class TestCaseController
    @Autowired
    constructor(private val testCaseService: TestCaseService){

    @PostMapping
    fun postTestCase(@AuthenticationPrincipal jwt: Jwt, @RequestBody testCaseInput: TestCaseInput): ResponseEntity<Output> {
        val userId=getUserId(jwt.subject)
        return try{
            ResponseEntity.ok(testCaseService.postTestCase(userId, jwt.tokenValue, testCaseInput))
        } catch(e: BadReqException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorOutput(e.message!!))
        } catch(e: NotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorOutput(e.message!!))
        }
    }

    @DeleteMapping("{testCaseId}")
    fun deleteTestCase(@AuthenticationPrincipal jwt: Jwt, @PathVariable testCaseId: String): ResponseEntity<String> {
        val userId=getUserId(jwt.subject)
        testCaseService.deleteTestCase(userId, jwt.tokenValue, testCaseId)
        return ResponseEntity.ok("")
    }
}