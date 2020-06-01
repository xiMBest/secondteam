package com.secondteam.rentauto.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.secondteam.rentauto.web.rest.TestUtil;

public class ReservecarTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reservecar.class);
        Reservecar reservecar1 = new Reservecar();
        reservecar1.setId(1L);
        Reservecar reservecar2 = new Reservecar();
        reservecar2.setId(reservecar1.getId());
        assertThat(reservecar1).isEqualTo(reservecar2);
        reservecar2.setId(2L);
        assertThat(reservecar1).isNotEqualTo(reservecar2);
        reservecar1.setId(null);
        assertThat(reservecar1).isNotEqualTo(reservecar2);
    }
}
