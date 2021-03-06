/*
 * Copyright 2012 4impact, Brisbane, Australia
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package au.com.ps4impact.madcow.runner.webdriver.blade

import au.com.ps4impact.madcow.grass.GrassBlade
import au.com.ps4impact.madcow.runner.webdriver.WebDriverStepRunner
import au.com.ps4impact.madcow.step.MadcowStep
import au.com.ps4impact.madcow.mappings.MadcowMappings
import org.junit.Test

import static groovy.test.GroovyAssert.*

/**
 * This class is a test case for verifying that the element doesn't exist
 *
 * @author Tom Romano
 */
class VerifyDoesNotExistTest extends AbstractBladeTestCase {

    def verifyDoesNotExist = new VerifyDoesNotExist();

    protected verifyDoesNotExistExecution(GrassBlade blade, boolean shouldPass) {
        (testCase.stepRunner as WebDriverStepRunner).driver.get("file://${testHtmlFilePath}");
        MadcowStep step = new MadcowStep(testCase, blade, null);
        testCase.stepRunner.execute(step);
        assertEquals(shouldPass, step.result.passed());
    }

    @Test
    void testLinkByHtmlId() {
        // defaults to html id
        GrassBlade blade = new GrassBlade('aLinkId.verifyDoesNotExist', testCase.grassParser);
        verifyDoesNotExistExecution(blade, false);

        // explicit id
        MadcowMappings.addMapping(testCase, 'mapping', ['id': 'aLinkId']);
        blade = new GrassBlade('mapping.verifyDoesNotExist', testCase.grassParser);
        verifyDoesNotExistExecution(blade, false);
    }

    @Test
    void testLinkByHtmlIdHidden() {
        // defaults to html id
        GrassBlade blade = new GrassBlade('hiddenTextInput.verifyDoesNotExist', testCase.grassParser);
        verifyDoesNotExistExecution(blade, true);
    }

    @Test
    void testLinkByName() {
        MadcowMappings.addMapping(testCase, 'aLinkName', ['name': 'aLinkName']);
        GrassBlade blade = new GrassBlade('aLinkName.verifyDoesNotExist', testCase.grassParser);
        verifyDoesNotExistExecution(blade, false);
    }

    @Test
    void testLinkByXPath() {
        MadcowMappings.addMapping(testCase, 'aLinkXPath', ['xpath': '//a[@id=\'aLinkId\']']);
        GrassBlade blade = new GrassBlade('aLinkXPath.verifyDoesNotExist', testCase.grassParser);
        verifyDoesNotExistExecution(blade, false);
    }

    @Test
    void testLinkByText() {
        MadcowMappings.addMapping(testCase, 'aLinkText', ['text': 'A link']);
        GrassBlade blade = new GrassBlade('aLinkText.verifyDoesNotExist', testCase.grassParser);
        verifyDoesNotExistExecution(blade, false);
    }

    @Test
    void testLinkDoesNotExist() {
        GrassBlade blade = new GrassBlade('aLinkThatDoesntExist.verifyDoesNotExist', testCase.grassParser);
        verifyDoesNotExistExecution(blade, true);
    }

    @Test
    void testLinkDoesNotExistByName() {
        MadcowMappings.addMapping(testCase, 'aLinkName', ['name': 'aLinkNameWhichIsMadeUp']);
        GrassBlade blade = new GrassBlade('aLinkName.verifyDoesNotExist', testCase.grassParser);
        verifyDoesNotExistExecution(blade, true);
    }

    @Test
    void testLinkDoesNotExistByXPath() {
        MadcowMappings.addMapping(testCase, 'aLinkXPath', ['xpath': '//a[@id=\'aLinkIdWhichIsPretend\']']);
        GrassBlade blade = new GrassBlade('aLinkXPath.verifyDoesNotExist', testCase.grassParser);
        verifyDoesNotExistExecution(blade, true);
    }

    @Test
    void testLinkDoesNotExistByText() {
        MadcowMappings.addMapping(testCase, 'aLinkText', ['text': 'A link text which does not exist']);
        GrassBlade blade = new GrassBlade('aLinkText.verifyDoesNotExist', testCase.grassParser);
        verifyDoesNotExistExecution(blade, true);
    }

    @Test
    void testDefaultMappingSelector() {
        GrassBlade blade = new GrassBlade('testsite_menu_createAddress.verifyDoesNotExist', testCase.grassParser);
        assertTrue(verifyDoesNotExist.isValidBladeToExecute(blade));
    }

    @Test
    void testMappingSelectorInvalidRequired() {
        try {
            GrassBlade blade = new GrassBlade('testsite_menu_createAddress.verifyDoesNotExist', testCase.grassParser);
            blade.mappingSelectorType = 'invalidOne';
            assertFalse(verifyDoesNotExist.isValidBladeToExecute(blade));
            fail('should always exception');
        } catch (e) {
            assertEquals('Unsupported mapping selector type \'invalidOne\'. Only [ID, TEXT, NAME, XPATH, CSS] are supported.', e.message);
        }
    }

    @Test
    void testMappingSelectorRequired() {
        try {
            GrassBlade blade = new GrassBlade('testsite_menu_createAddress.verifyDoesNotExist', testCase.grassParser);
            blade.mappingSelectorType = null;
            assertFalse(verifyDoesNotExist.isValidBladeToExecute(blade));
            fail('should always exception');
        } catch (e) {
            assertEquals('Mapping selector must be supplied. One of [ID, TEXT, NAME, XPATH, CSS] are supported.', e.message);
        }
    }

    @Test
    void testEquationNotSupported() {
        try {
            GrassBlade blade = new GrassBlade('testsite_menu_createAddress.verifyDoesNotExist = yeah yeah', testCase.grassParser);
            assertFalse(verifyDoesNotExist.isValidBladeToExecute(blade));
            fail('should always exception');
        } catch (e) {
            assertEquals('Unsupported grass format. Only grass blades of type \'[STATEMENT]\' are supported.', e.message);
        }
    }
}
