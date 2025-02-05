package org.mines.address.port.driving;

import org.mines.address.domain.dto.PhoneSearchRequestDto;
import org.mines.address.domain.model.Phone;

import java.util.*;


public interface PhoneUseCase {

    List<Phone> searchPhones(PhoneSearchRequestDto phoneSearchRequestDto);

}