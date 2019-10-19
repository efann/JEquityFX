/*
 * JEquity
 * Copyright(c) 2008-2019, Beowurks
 * Original Author: Eddie Fann
 * License: Eclipse Public License - v 2.0 (https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html)
 *
 */
package com.beowurks.jequity.dao.combobox;

// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
public class StringKeyItem
{

  private final String fcKey;
  private final String fcDescription;

  // ---------------------------------------------------------------------------------------------------------------------
  // By the way, according to StackOverFlow, you shouldn't override toString as it causes problems down the road.
  public StringKeyItem(final String tcKey, final String tcDescription)
  {
    this.fcKey = tcKey.trim();
    this.fcDescription = tcDescription.trim();
  }

  // ---------------------------------------------------------------------------------------------------------------------
  public String getKey()
  {
    return (this.fcKey);
  }

  // ---------------------------------------------------------------------------------------------------------------------
  public String getDescription()
  {
    return (this.fcDescription);
  }

  // ---------------------------------------------------------------------------------------------------------------------
}
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------

