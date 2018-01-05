/*
 * JEquity
 * Copyright(c) 2008-2018, Beowurks
 * Original Author: Eddie Fann
 * License: Eclipse Public License - v 2.0 (https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html)
 *
 */

package com.beowurks.jequity.controller.tab;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
abstract public class TabBaseController
{
  // -----------------------------------------------------------------------------
  abstract public void refreshData();

  // ---------------------------------------------------------------------------------------------------------------------
  abstract protected void setupTables();

  // -----------------------------------------------------------------------------
  abstract protected NativeQuery getQuery(final Session toSession);

  // ---------------------------------------------------------------------------------------------------------------------

}
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
