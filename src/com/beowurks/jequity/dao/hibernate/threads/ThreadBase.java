/*
 * JEquity
 * Copyright(c) 2008-2021, Beowurks
 * Original Author: Eddie Fann
 * License: Eclipse Public License - v 2.0 (https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html)
 *
 */

package com.beowurks.jequity.dao.hibernate.threads;

// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
public class ThreadBase
{
  protected Thread foThread = null;

  protected boolean flSuccess = false;
  protected boolean flDisplayDialogMessage = true;

  protected String fcErrorMessage = "";

  // ---------------------------------------------------------------------------------------------------------------------
  public ThreadBase()
  {
  }

  // ---------------------------------------------------------------------------------------------------------------------
  public Thread getThread()
  {
    return (this.foThread);
  }

  // ---------------------------------------------------------------------------------------------------------------------
  public boolean isSuccessful()
  {
    return (this.flSuccess);
  }

  // ---------------------------------------------------------------------------------------------------------------------
  public String getErrorMessage()
  {
    return (this.fcErrorMessage);
  }

}
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
