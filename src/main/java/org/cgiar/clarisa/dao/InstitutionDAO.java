/*****************************************************************
 * This file is part of CGIAR Level Agricultural Results
 * Interoperable System Architecture (CLARISA).
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

package org.cgiar.clarisa.dao;

import org.cgiar.clarisa.model.Institution;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

public interface InstitutionDAO extends JpaRepository<Institution, Long> {

  @Query("select i from Institution i where i.parent is null and i.id!= ?1 and i.institutionParent.id is null ")
  public List<Institution> searchChildInstitutions(Long parent);


  /**
   * Search an institution, given a search value
   * 
   * @param searchValue the search value
   * @return a list of institutions, if any; else, null
   */
  @Query("select i from Institution i where i.name like concat('%', ?1, '%') or i.acronym like "
    + "concat('%', ?1, '%') or i.websiteLink like concat('%', ?1, '%') "
    + "group by i.name, i.acronym, i.websiteLink order by (case when i.name like concat(?1, '%') then 0 "
    + "when i.name like concat('% %', ?1, '% %') then 3 when i.name like concat('%', ?1) then 6 "
    + "when i.acronym like concat(?1, '%') then 1 when i.acronym like concat('% %', ?1, '% %') then 4 "
    + "when i.acronym like concat('%', ?1) then 7 when i.websiteLink like concat(?1, '%') then 2 "
    + "when i.websiteLink like concat('% %', ?1, '% %') then 5 when i.websiteLink like concat('%', ?1) then 8 "
    + "else 12 end), i.name, i.acronym, i.websiteLink")
  public List<Institution> searchInstitution(String searchValue);

  @Query("select i from Institution i where i.institutionParent.id= ?1")
  public List<Institution> searchInstitutionParent(Long parent);
}
