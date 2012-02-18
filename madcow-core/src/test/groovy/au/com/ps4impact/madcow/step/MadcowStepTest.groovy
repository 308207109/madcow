package au.com.ps4impact.madcow.step

import au.com.ps4impact.madcow.MadcowTestCase

/**
 * Test class for MadcowStep.
 */
class MadcowStepTest extends GroovyTestCase {

    void testToString() {
        assertToString(new MadcowStep(new MadcowTestCase('StepTest'), null, null), "[testCase: StepTest, blade: null, parent: null, children: []]") ;
    }
}
