package org.approvaltests.writers;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.scrubbers.RegExScrubber;
import org.junit.jupiter.api.Test;

public class ApprovalXmlWriterTest
{
  @Test
  public void plainXml() throws Exception
  {
    Approvals.verifyXml("<xml><hello/><start>hi</start></xml>");
  }
  @Test
  public void xmlWithAttributes() throws Exception
  {
    Approvals.verifyXml("<xml b=\"123\" a=\"456\"><hello x=\"y\"/><start>hi</start></xml>");
  }
  @Test
  public void xmlWithDeepAttributes() throws Exception
  {
    Approvals
        .verifyXml("<xml b=\"1\" a=\"1\"><branch1 b=\"1\" a=\"1\"/><branch2 b=\"1\" a=\"1\">hi</branch2></xml>");
  }
  @Test
  public void xmlWithDeepAttributesWithScrubber() throws Exception
  {
    Approvals.verifyXml(
        "<xml b=\"1\" a=\"1\"><branch1 b=\"1\" a=\"1\"/><branch2 b=\"1\" a=\"1\">hi</branch2></xml>",
        new Options(new RegExScrubber("hi", "hello")));
  }
  @Test
  public void invalidXml() throws Exception
  {
    Approvals.verifyXml("<xml><hello/><start>hi</xml>");
    System.err.println("Note: The previous xml error (</start>) is expected ");
  }
}
