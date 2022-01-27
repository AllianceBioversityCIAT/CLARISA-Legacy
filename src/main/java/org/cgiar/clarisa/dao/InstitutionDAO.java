/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
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
}
