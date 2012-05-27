package au.com.ps4impact.madcow.runner.webdriver.blade

import au.com.ps4impact.madcow.grass.GrassBlade
import au.com.ps4impact.madcow.MadcowTestCase
import au.com.ps4impact.madcow.step.MadcowStep
import au.com.ps4impact.madcow.config.MadcowConfig
import au.com.ps4impact.madcow.util.ResourceFinder
import au.com.ps4impact.madcow.runner.webdriver.WebDriverStepRunner
import au.com.ps4impact.madcow.mappings.MadcowMappings

/**
 * Test for the WaitFor BladeRunner.
 *
 * @author Gavin Bunney
 */
class WaitForTest extends GroovyTestCase {

    MadcowTestCase testCase = new MadcowTestCase('WaitForTest', new MadcowConfig(), []);
    WaitFor waitFor = new WaitFor();
    String testHtmlFilePath = ResourceFinder.locateFileOnClasspath(this.class.classLoader, 'test.html', 'html').absolutePath;

    protected verifyWaitFor(GrassBlade blade, boolean shouldPass) {
        (testCase.stepRunner as WebDriverStepRunner).driver.get("file://${testHtmlFilePath}");
        MadcowStep step = new MadcowStep(testCase, blade, null);
        testCase.stepRunner.execute(step);
        assertEquals(shouldPass, step.result.passed());
    }

    void testWaitForByHtmlId() {
        // defaults to html id
        GrassBlade blade = new GrassBlade('aLinkId.waitFor', testCase.grassParser);
        verifyWaitFor(blade, true);

        // explicit htmlid
        MadcowMappings.addMapping(testCase, 'aLinkId', ['id': 'aLinkId']);
        blade = new GrassBlade('aLinkId.waitFor', testCase.grassParser);
        verifyWaitFor(blade, true);
    }

    void testWaitForIncorrect() {
        GrassBlade blade = new GrassBlade('aLinkId.waitFor = A link that isn\'t a link is still a link', testCase.grassParser);
        verifyWaitFor(blade, false);
    }

    void testWaitForByName() {
        MadcowMappings.addMapping(testCase, 'aLinkName', ['name': 'aLinkName']);
        GrassBlade blade = new GrassBlade('aLinkName.waitFor = A link', testCase.grassParser);
        verifyWaitFor(blade, true);
    }

    void testWaitForByXPath() {
        MadcowMappings.addMapping(testCase, 'aLinkXPath', ['xpath': '//a[@id=\'aLinkId\']']);
        GrassBlade blade = new GrassBlade('aLinkXPath.waitFor = A link', testCase.grassParser);
        verifyWaitFor(blade, true);
    }

    void testWaitForByText() {
        MadcowMappings.addMapping(testCase, 'aLinkText', ['text': 'A link']);
        GrassBlade blade = new GrassBlade('aLinkText.waitFor = A link', testCase.grassParser);
        verifyWaitFor(blade, true);
    }

    void testWaitForEmpty() {
        GrassBlade blade = new GrassBlade('anEmptyParagraphId.waitFor = ', testCase.grassParser);
        verifyWaitFor(blade, true);
    }

    void testWaitForPageText() {
        GrassBlade blade = new GrassBlade('waitFor = Madcow WebDriver Runner Test HTML', testCase.grassParser);
        verifyWaitFor(blade, true);
    }
}