package com.example.nada.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(title = "DS Catalog API",
                description = "all process ffrom dsCatalog",
                version = "1.0.0",
                contact = @Contact(
                        name="Anderson Costa",
                        email="acuondala@gmail.com"
                )

        )
)
public class OpenApi {
}
