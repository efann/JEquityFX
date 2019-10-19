/*
 * JEquity
 * Copyright(c) 2008-2019, Beowurks
 * Original Author: Eddie Fann
 * License: Eclipse Public License - v 2.0 (https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html)
 *
 */

package com.beowurks.jequity.view.textfield;

import com.beowurks.jequity.utility.Constants;
import javafx.scene.control.TextField;

// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
public class TextFieldPlus extends TextField
{

  // ---------------------------------------------------------------------------------------------------------------------
  public TextFieldPlus()
  {
    super();
  }

  // ---------------------------------------------------------------------------------------------------------------------
  public TextFieldPlus(final String tcText)
  {
    super(tcText);
  }

  // ---------------------------------------------------------------------------------------------------------------------
  public void setReadOnly(final boolean tlReadOnly)
  {
    this.setEditable(!tlReadOnly);

    this.setStyle(tlReadOnly ? Constants.DISABLED_CONTROL_BACKGROUND : "");
  }
  // ---------------------------------------------------------------------------------------------------------------------

}
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
