package com.kendelong.functional

import static org.junit.Assert.*
import groovy.json.JsonOutput

class BaseTestClass
{
	protected def standardFailure
	protected def shouldHaveFailed
	protected def failureBut200
	
	BaseTestClass()
	{
		standardFailure =  { resp -> println resp.entity.writeTo(System.out); fail("Bad response: ${resp.statusLine.statusCode}")}
		shouldHaveFailed = { resp, json -> fail("should have failed")}
		failureBut200 = { resp, json -> assertEquals('failure', json.status)}
	}
	
	protected void prettyPrint(def json)
	{
		println "\nOutput from ${this.class.simpleName}"
		println JsonOutput.prettyPrint(JsonOutput.toJson(json))
	}

}
