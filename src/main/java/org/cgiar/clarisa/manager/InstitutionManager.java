/*****************************************************************
 * This file is part of CGIAR Level Agricultural Results
 * Interoperable System Architecture Platform (CLARISA).
 * CLARISA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CLARISA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CLARISA. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.clarisa.manager;

import org.cgiar.clarisa.model.Institution;

import java.util.List;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

public interface InstitutionManager extends GenericManager<Institution> {

  /**
   * Search an institution, given a search value
   * 
   * @param searchValue the search value
   * @return a list of institutions, if any; else, null
   */
  public List<Institution> searchInstitution(String searchValue);
}