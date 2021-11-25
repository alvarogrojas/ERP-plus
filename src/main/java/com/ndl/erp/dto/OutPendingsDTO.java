package com.ndl.erp.dto;

import java.util.List;


    public class OutPendingsDTO {

        private Integer id;
        private String type;

        private List<DetailsDTO> details;

        public OutPendingsDTO() {
        }

        public OutPendingsDTO(String type,Integer id) {
            this.id = id;
            this.type = type;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public List<DetailsDTO> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsDTO> details) {
            this.details = details;
        }
    }

