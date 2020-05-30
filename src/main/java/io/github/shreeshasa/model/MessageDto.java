package io.github.shreeshasa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author shreeshasa
 */
@Data
public class MessageDto {

  @JsonProperty ("connection_id")
  private String connectionId;

  @JsonProperty ("user_id")
  private String userId;
}
