package au.com.ps4impact.madcow.runner.webdriver

import au.com.ps4impact.madcow.MadcowTestCase

/**
 * Test for running WebDriver grass
 */
class WebDriverStepRunnerTest extends GroovyTestCase {

    public void testRunIt() {
        String grassScriptString = """
            invokeUrl = http://www.4impact.com.au
            @expectedValue = bum on a seat

            # verify the text exists on the page
            verifyText = @expectedValue
        """;
        ArrayList<String> grassScript = new ArrayList<String>();
        grassScriptString.eachLine { line -> grassScript.add(line) }

        MadcowTestCase testCase = new MadcowTestCase(grassScript);
        testCase.execute();
        println("Results!!!! : " + testCase.steps.result);
    }

}
