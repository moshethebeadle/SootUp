package sootup.callgraph;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2019-2020 Linghui Luo, Christian Brüggemann, Ben Hermann, Markus Schmidt
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import javax.annotation.Nonnull;
import sootup.core.views.View;

/**
 * A relaxed version of the Class Hierarchy Analysis call graph algorithm that includes calls to the
 * interfaces, not just subclasses. This is to support frameworks such as Spring Data.
 */
public class RelaxedClassHierarchyAnalysisAlgorithm extends ClassHierarchyAnalysisAlgorithm {

  /**
   * The constructor of the CHA algorithm.
   *
   * @param view it contains the data of the classes and methods
   */
  public RelaxedClassHierarchyAnalysisAlgorithm(@Nonnull View view) {
    super(view);
  }

  @Override
  protected boolean includeAbstractMethods() {
    return true;
  }
}
