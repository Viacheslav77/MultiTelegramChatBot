package com.info.chatbot.specification;

import com.info.chatbot.constants.ApplicationConstant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.IdentifiableType;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class BaseSpecification {

  private static final Class<?>[] CLASSES_TO_SEARCH = {String.class, Integer.class, Date.class, Long.class, ApplicationConstant.class};

  private String getSearchString(String originalString) {
    return ("%" + originalString + "%").toUpperCase();
  }

  private String getStartsWithSearchString(String originalString) {
    return (originalString + "%").toUpperCase();
  }

  private boolean isClassAssignableFromAttribute(SingularAttribute<?, ?> attribute, Class<?>... classes) {
    return Stream.of(classes).anyMatch(attributeClass -> attributeClass.isAssignableFrom(attribute.getJavaType()));
  }

  protected <T> Predicate[] getEntityFieldLikeRequestParamPredicatesArray(
      EntityType<T> type, Map<String, String> requestParams, CriteriaBuilder criteriaBuilder, Path<T> pathToEntity) {
    return getSingularAttributesStream(type)
        .filter(attribute -> isClassAssignableFromAttribute(attribute, CLASSES_TO_SEARCH))
        .map(Attribute::getName)
        .filter(attributeName -> StringUtils.hasText(requestParams.get(attributeName)))
        .map(attributeName -> criteriaBuilder.like(
            criteriaBuilder.upper(pathToEntity.get(attributeName).as(String.class)),
            getSearchString(requestParams.get(attributeName))))
        .toArray(Predicate[]::new);
  }

  protected <T> Predicate[] getEntityFieldEqualRequestParamPredicatesArray(
          EntityType<T> type, Map<String, String> requestParams, CriteriaBuilder criteriaBuilder, Path<T> pathToEntity) {
    return getSingularAttributesStream(type)
        .filter(attribute -> isClassAssignableFromAttribute(attribute, CLASSES_TO_SEARCH))
        .map(Attribute::getName)
        .filter(attributeName -> StringUtils.hasText(requestParams.get(attributeName)))
        .map(attributeName -> criteriaBuilder.equal(
            criteriaBuilder.upper(pathToEntity.get(attributeName).as(String.class)),
            getSearchString(requestParams.get(attributeName))))
        .toArray(Predicate[]::new);
  }

  private <T> Stream<SingularAttribute<? super T, ?>> getSingularAttributesStream(EntityType<T> type) {
    Stream<SingularAttribute<? super T, ?>> singularAttributes = type.getSingularAttributes().stream();
    IdentifiableType<? super T> supertype = type.getSupertype();
    while (supertype != null) {
      singularAttributes = Stream.concat(singularAttributes, supertype.getDeclaredSingularAttributes().stream());
      supertype = supertype.getSupertype();
    }
    return singularAttributes;
  }

  public <T> Specification<T> entityFieldLike(SingularAttribute<T, String> paramName, String searchParam) {
    return (Specification<T>) (root, query, cb) ->
        cb.like(cb.upper(root.get(paramName).as(String.class)), getSearchString(searchParam));
  }


  public <T> Specification<T> entityFieldStartsWith(SingularAttribute<? super T, String> paramName, String searchParam) {
    return (Specification<T>) (root, query, cb) ->
        cb.like(cb.upper(root.get(paramName).as(String.class)), getStartsWithSearchString(searchParam));
  }


  public <T, Y> Specification<T> entityFieldEqualsParam(SingularAttribute<? super T, Y> paramName, Y paramValue) {
    return (Specification<T>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(paramName), paramValue);
  }

  public <T, Y> Specification<T> entityFieldInParams(SingularAttribute<? super T, Y> paramName, List<Y> paramValue) {
    return (Specification<T>) (root, query, criteriaBuilder) -> root.get(paramName).in(paramValue);
  }

  public <T, Y> Specification<T> entityFieldNotEqualsParam(SingularAttribute<? super T, Y> paramName, Y paramValue) {
    return (Specification<T>) (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(paramName), paramValue);
  }

  public <T> Specification<T> entityDateFieldLessThanOrEqual(SingularAttribute<? super T, Date> paramName, Date paramValue) {
    return (Specification<T>) (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get(paramName), paramValue);
  }

  public <T> Specification<T> entityDateFieldLessThen(SingularAttribute<? super T, Date> paramName, Date paramValue) {
    return (Specification<T>) (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThan(root.get(paramName), paramValue);
  }

  public <T> Specification<T> entityDateFieldLessThen(
      SingularAttribute<? super T, LocalDate> paramName, LocalDate paramValue) {
    return (Specification<T>) (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThan(root.get(paramName), paramValue);
  }

  public <T> Specification<T> entityDateFieldGreaterThanOrEqual(
      SingularAttribute<? super T, Date> paramName, Date paramValue) {
    return (Specification<T>) (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get(paramName), paramValue);
  }

  public <T> Specification<T> entityDateFieldGreaterThanOrEqual(
      SingularAttribute<? super T, LocalDate> paramName, LocalDate paramValue) {
    return (Specification<T>) (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get(paramName), paramValue);
  }

  public <T> Specification<T> entityFieldGreaterThan(SingularAttribute<? super T, Integer> paramName, Integer paramValue) {
    return (Specification<T>) (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThan(root.get(paramName), paramValue);
  }

  public <T> Specification<T> entityFieldLessThan(SingularAttribute<? super T, Integer> paramName, Integer paramValue) {
    return (Specification<T>) (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThan(root.get(paramName), paramValue);
  }

  public  <T> Specification<T>  and(Specification<T> source,Specification<T> target){
    if(source == null){
      return target;
    }
    return source.and(target);
  }

  public  <T> Specification<T>  or(Specification<T> source,Specification<T> target){
    if(source == null){
      return target;
    }
    return source.or (target);
  }

}
