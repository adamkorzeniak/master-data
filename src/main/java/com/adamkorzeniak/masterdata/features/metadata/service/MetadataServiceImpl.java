package com.adamkorzeniak.masterdata.features.metadata.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.adamkorzeniak.masterdata.exception.exceptions.MetadataResourceException;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.MetadataResponse;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.Module;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.Operation2;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.OperationResponse;
import com.adamkorzeniak.masterdata.features.metadata.model.dto.RequestBody;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.ComponentSchema;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Info;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Metadata;
import com.adamkorzeniak.masterdata.features.metadata.model.openapi.Operation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class MetadataServiceImpl implements MetadataService {

	@Value("${api.yaml.file}")
	private String FILE_PATH;

	@Override
	public Metadata buildMetadata() {
		Metadata metadata = readMetadataFromFile();
		validate(metadata);
		return metadata;
	}

	@Override
	public MetadataResponse buildResponse(Metadata metadata) {
		MetadataResponse response = new MetadataResponse();
		
		response.setModules(metadata.getTags().stream()
			.map(t -> {
				Module m = new Module();
				m.setName(t.getName());
				m.setDescription(t.getDescription());
				m.setOperations(new ArrayList<>());
				return m;
			}).collect(Collectors.toList()));

		for (Map.Entry<String, Map<String, Operation>> entry : metadata.getPaths().entrySet()) {
			String resourceUrl = entry.getKey();
			Map<String, Operation> operations = entry.getValue();
			for (Map.Entry<String, Operation> operation : operations.entrySet()) {
				Operation2 op2 = new Operation2();
				op2.setMethod(operation.getKey().toUpperCase());
				op2.setPath(resourceUrl);
				op2.setDescription(operation.getValue().getDescription());
				op2.setOperationId(operation.getValue().getOperationId());
				op2.setSummary(operation.getValue().getSummary());
				
				
				if (operation.getValue().getRequestBody() != null) {
					RequestBody body = new RequestBody();
					String bodyReference = operation.getValue().getRequestBody().getRef().replace("#/components/requestBodies/", "");
					com.adamkorzeniak.masterdata.features.metadata.model.openapi.RequestBody requestBody = metadata.getComponents().getRequestBodies().entrySet().stream()
							.filter(b -> bodyReference.equals(b.getKey()))
							.findFirst()
							.map(b -> b.getValue()).get();
					
					body.setDescription(requestBody.getDescription());
					String schemaReference = requestBody.getContent().get("application/json").getSchema().getRef().replace("#/components/schemas/", "");
                    ComponentSchema componentSchema = metadata.getComponents().getSchemas().entrySet().stream()
                            .filter(s -> schemaReference.equals(s.getKey()))
                            .findFirst()
                            .map(s -> s.getValue()).get();
					op2.setRequestBody(body);
				}
				
				
				response.getModules().stream()
					.filter(m -> operation.getValue().getTags().contains(m.getName()))
					.forEach(m -> m.getOperations().add(op2));
			}
		}
		return response;
	}

//    private List<Module> buildModules(Metadata metadata) {
//        List<Module> modules = new ArrayList<>();
//
//        List<Tag> tags = metadata.getTags();
//
//        for (Tag tag : tags) {
//            Module module = new Module();
//            module.setName(tag.getName());
//            module.setDescription(tag.getDescription());
//            modules.add(module);
//        }
//
//        Map<String, Map<String, Operation>> paths = metadata.getPaths();
//        for (Map.Entry<String, Map<String, Operation>> entry : paths.entrySet()) {
//            String resourceUrl = entry.getKey();
//            Map<String, Operation> operations = entry.getValue();
//            for (Map.Entry<String, Operation> operation : operations.entrySet()) {
//                addOperationToModules(modules, operation);
//            }
//        }
//
//        return modules;
//    }

//    private void addOperationToModules(List<Module> modules, Map.Entry<String, Operation> operation) {
//        String method = operation.getKey();
//        Operation operationValue = operation.getValue();
//        List<String> tags = operationValue.getTags();
//
//        OperationResponse operationResponse = new OperationResponse();
//        operationResponse.setSummary(operationValue.getSummary());
//        operationResponse.setOperationId(operationValue.getOperationId());
//        operationResponse.setDescription(operationValue.getDescription());
//
////		modules.stream()
////			.filter(m -> tags.contains(m.getName()))
////			.forEach(m -> m.addResource(operationResponse));
//
//    }

	private Metadata readMetadataFromFile() {
		ObjectMapper mapper = new ObjectMapper();
		InputStream is = null;
		try {
			File file = ResourceUtils.getFile(FILE_PATH);
			is = new FileInputStream(file);
			return mapper.readValue(is, Metadata.class);
		} catch (IOException e) {
			e.printStackTrace();
			throw new MetadataResourceException();
		}
	}

	private void validate(Metadata metadata) {
		// TODO: consider if needed
	}
}
