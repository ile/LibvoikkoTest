package com.example;

import org.junit.Test;
import org.puimula.libvoikko.*;

import java.util.List;
import static org.junit.Assert.*;

public class LibVoikkoTest {

    @Test
    public void testVoikkoInitialization() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            assertNotNull(voikko);

        } catch (Exception e) {
            fail("Exception during Voikko initialization: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testVoikkoInitializationWithPath() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi", ".");
            assertNotNull(voikko);

        } catch (Exception e) {
            fail("Exception during Voikko initialization with path: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testAnalyzeWord() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            List<Analysis> results = voikko.analyze("kissat");
            assertNotNull(results);
            assertTrue(!results.isEmpty());
            for (Analysis analysis : results) {
                assertTrue(analysis.containsKey("BASEFORM"));
            }
        } catch (Exception e) {
            fail("Exception during Voikko analyze: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testHyphenate() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            String hyphenated = voikko.hyphenate("tietokone", "-", false);
            assertNotNull(hyphenated);
            assertTrue(!hyphenated.isEmpty());
            assertTrue(hyphenated.contains("-"));
        } catch (Exception e) {
            fail("Exception during Voikko hyphenate: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testHyphenateWithNoUglyHyphenation() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            voikko.setNoUglyHyphenation(true);
            String hyphenated = voikko.hyphenate("tietokone", "-", false);
            assertNotNull(hyphenated);
            assertTrue(!hyphenated.isEmpty());
            assertTrue(hyphenated.contains("-"));
        } catch (Exception e) {
            fail("Exception during Voikko hyphenate with no ugly hyphenation: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testSpellCheck() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            assertTrue(voikko.spell("kissa"));
            assertFalse(voikko.spell("kasssa"));
        } catch (Exception e) {
            fail("Exception during Voikko spell check: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testSuggest() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            List<String> suggestions = voikko.suggest("kasssa");
            System.out.println("suggestions" + suggestions);
            assertNotNull(suggestions);
            assertFalse(suggestions.isEmpty());
            assertTrue(suggestions.contains("kassa"));
        } catch (Exception e) {
            fail("Exception during Voikko suggest: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testTokens() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            List<Token> tokens = voikko.tokens("kissa istuu.");
            assertNotNull(tokens);
            assertFalse(tokens.isEmpty());
            assertEquals(4, tokens.size());
            assertEquals("kissa", tokens.get(0).getText());
            assertEquals(TokenType.WORD, tokens.get(0).getType());
            assertEquals(" ", tokens.get(1).getText());
            assertEquals(TokenType.WHITESPACE, tokens.get(1).getType());
            assertEquals("istuu", tokens.get(2).getText());
            assertEquals(TokenType.WORD, tokens.get(2).getType());
            assertEquals(".", tokens.get(3).getText());
            assertEquals(TokenType.PUNCTUATION, tokens.get(3).getType());

        } catch (Exception e) {
            fail("Exception during Voikko tokens: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testSentences() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            List<Sentence> sentences = voikko.sentences("kissa istuu. Koira juoksee");
            assertNotNull(sentences);
            assertFalse(sentences.isEmpty());
            assertEquals(2, sentences.size());
            assertEquals("kissa istuu. ", sentences.get(0).getText());
            assertEquals(SentenceStartType.PROBABLE, sentences.get(0).getNextStartType());
            assertEquals("Koira juoksee", sentences.get(1).getText());
            assertEquals(SentenceStartType.NONE, sentences.get(1).getNextStartType());
        } catch (Exception e) {
            fail("Exception during Voikko sentences: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testSetIgnoreDot() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            voikko.setIgnoreDot(true);
            assertTrue(voikko.spell("kissa."));
        } catch (Exception e) {
            fail("Exception during Voikko setIgnoreDot: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testSetAcceptMissingHyphens() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            voikko.setAcceptMissingHyphens(true);
            assertFalse(voikko.spell("tieto-kone"));
            assertTrue(voikko.spell("tietokone"));
        } catch (Exception e) {
            fail("Exception during Voikko setAcceptMissingHyphens: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

    @Test
    public void testSetIgnoreNumbers() {
        Voikko voikko = null;
        try {
            voikko = new Voikko("fi");
            voikko.setIgnoreNumbers(true);
            assertTrue(voikko.spell("kissa123"));
        } catch (Exception e) {
            fail("Exception during Voikko setIgnoreNumbers: " + e.getMessage());
        } finally {
            if (voikko != null) {
                voikko.terminate();
            }
        }
    }

}