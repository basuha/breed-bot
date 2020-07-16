package com.basuha.breed_bot.message;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "affenpinscher",
        "african",
        "airedale",
        "akita",
        "appenzeller",
        "australian",
        "basenji",
        "beagle",
        "bluetick",
        "borzoi",
        "bouvier",
        "boxer",
        "brabancon",
        "briard",
        "buhund",
        "bulldog",
        "bullterrier",
        "cairn",
        "cattledog",
        "chihuahua",
        "chow",
        "clumber",
        "cockapoo",
        "collie",
        "coonhound",
        "corgi",
        "cotondetulear",
        "dachshund",
        "dalmatian",
        "dane",
        "deerhound",
        "dhole",
        "dingo",
        "doberman",
        "elkhound",
        "entlebucher",
        "eskimo",
        "finnish",
        "frise",
        "germanshepherd",
        "greyhound",
        "groenendael",
        "havanese",
        "hound",
        "husky",
        "keeshond",
        "kelpie",
        "komondor",
        "kuvasz",
        "labrador",
        "leonberg",
        "lhasa",
        "malamute",
        "malinois",
        "maltese",
        "mastiff",
        "mexicanhairless",
        "mix",
        "mountain",
        "newfoundland",
        "otterhound",
        "ovcharka",
        "papillon",
        "pekinese",
        "pembroke",
        "pinscher",
        "pitbull",
        "pointer",
        "pomeranian",
        "poodle",
        "pug",
        "puggle",
        "pyrenees",
        "redbone",
        "retriever",
        "ridgeback",
        "rottweiler",
        "saluki",
        "samoyed",
        "schipperke",
        "schnauzer",
        "setter",
        "sheepdog",
        "shiba",
        "shihtzu",
        "spaniel",
        "springer",
        "stbernard",
        "terrier",
        "vizsla",
        "waterdog",
        "weimaraner",
        "whippet",
        "wolfhound"
})
public class BreedList {
    @JsonProperty("affenpinscher")
    public List<Object> affenpinscher = null;
    @JsonProperty("african")
    public List<Object> african = null;
    @JsonProperty("airedale")
    public List<Object> airedale = null;
    @JsonProperty("akita")
    public List<Object> akita = null;
    @JsonProperty("appenzeller")
    public List<Object> appenzeller = null;
    @JsonProperty("australian")
    public List<String> australian = null;
    @JsonProperty("basenji")
    public List<Object> basenji = null;
    @JsonProperty("beagle")
    public List<Object> beagle = null;
    @JsonProperty("bluetick")
    public List<Object> bluetick = null;
    @JsonProperty("borzoi")
    public List<Object> borzoi = null;
    @JsonProperty("bouvier")
    public List<Object> bouvier = null;
    @JsonProperty("boxer")
    public List<Object> boxer = null;
    @JsonProperty("brabancon")
    public List<Object> brabancon = null;
    @JsonProperty("briard")
    public List<Object> briard = null;
    @JsonProperty("buhund")
    public List<String> buhund = null;
    @JsonProperty("bulldog")
    public List<String> bulldog = null;
    @JsonProperty("bullterrier")
    public List<String> bullterrier = null;
    @JsonProperty("cairn")
    public List<Object> cairn = null;
    @JsonProperty("cattledog")
    public List<String> cattledog = null;
    @JsonProperty("chihuahua")
    public List<Object> chihuahua = null;
    @JsonProperty("chow")
    public List<Object> chow = null;
    @JsonProperty("clumber")
    public List<Object> clumber = null;
    @JsonProperty("cockapoo")
    public List<Object> cockapoo = null;
    @JsonProperty("collie")
    public List<String> collie = null;
    @JsonProperty("coonhound")
    public List<Object> coonhound = null;
    @JsonProperty("corgi")
    public List<String> corgi = null;
    @JsonProperty("cotondetulear")
    public List<Object> cotondetulear = null;
    @JsonProperty("dachshund")
    public List<Object> dachshund = null;
    @JsonProperty("dalmatian")
    public List<Object> dalmatian = null;
    @JsonProperty("dane")
    public List<String> dane = null;
    @JsonProperty("deerhound")
    public List<String> deerhound = null;
    @JsonProperty("dhole")
    public List<Object> dhole = null;
    @JsonProperty("dingo")
    public List<Object> dingo = null;
    @JsonProperty("doberman")
    public List<Object> doberman = null;
    @JsonProperty("elkhound")
    public List<String> elkhound = null;
    @JsonProperty("entlebucher")
    public List<Object> entlebucher = null;
    @JsonProperty("eskimo")
    public List<Object> eskimo = null;
    @JsonProperty("finnish")
    public List<String> finnish = null;
    @JsonProperty("frise")
    public List<String> frise = null;
    @JsonProperty("germanshepherd")
    public List<Object> germanshepherd = null;
    @JsonProperty("greyhound")
    public List<String> greyhound = null;
    @JsonProperty("groenendael")
    public List<Object> groenendael = null;
    @JsonProperty("havanese")
    public List<Object> havanese = null;
    @JsonProperty("hound")
    public List<String> hound = null;
    @JsonProperty("husky")
    public List<Object> husky = null;
    @JsonProperty("keeshond")
    public List<Object> keeshond = null;
    @JsonProperty("kelpie")
    public List<Object> kelpie = null;
    @JsonProperty("komondor")
    public List<Object> komondor = null;
    @JsonProperty("kuvasz")
    public List<Object> kuvasz = null;
    @JsonProperty("labrador")
    public List<Object> labrador = null;
    @JsonProperty("leonberg")
    public List<Object> leonberg = null;
    @JsonProperty("lhasa")
    public List<Object> lhasa = null;
    @JsonProperty("malamute")
    public List<Object> malamute = null;
    @JsonProperty("malinois")
    public List<Object> malinois = null;
    @JsonProperty("maltese")
    public List<Object> maltese = null;
    @JsonProperty("mastiff")
    public List<String> mastiff = null;
    @JsonProperty("mexicanhairless")
    public List<Object> mexicanhairless = null;
    @JsonProperty("mix")
    public List<Object> mix = null;
    @JsonProperty("mountain")
    public List<String> mountain = null;
    @JsonProperty("newfoundland")
    public List<Object> newfoundland = null;
    @JsonProperty("otterhound")
    public List<Object> otterhound = null;
    @JsonProperty("ovcharka")
    public List<String> ovcharka = null;
    @JsonProperty("papillon")
    public List<Object> papillon = null;
    @JsonProperty("pekinese")
    public List<Object> pekinese = null;
    @JsonProperty("pembroke")
    public List<Object> pembroke = null;
    @JsonProperty("pinscher")
    public List<String> pinscher = null;
    @JsonProperty("pitbull")
    public List<Object> pitbull = null;
    @JsonProperty("pointer")
    public List<String> pointer = null;
    @JsonProperty("pomeranian")
    public List<Object> pomeranian = null;
    @JsonProperty("poodle")
    public List<String> poodle = null;
    @JsonProperty("pug")
    public List<Object> pug = null;
    @JsonProperty("puggle")
    public List<Object> puggle = null;
    @JsonProperty("pyrenees")
    public List<Object> pyrenees = null;
    @JsonProperty("redbone")
    public List<Object> redbone = null;
    @JsonProperty("retriever")
    public List<String> retriever = null;
    @JsonProperty("ridgeback")
    public List<String> ridgeback = null;
    @JsonProperty("rottweiler")
    public List<Object> rottweiler = null;
    @JsonProperty("saluki")
    public List<Object> saluki = null;
    @JsonProperty("samoyed")
    public List<Object> samoyed = null;
    @JsonProperty("schipperke")
    public List<Object> schipperke = null;
    @JsonProperty("schnauzer")
    public List<String> schnauzer = null;
    @JsonProperty("setter")
    public List<String> setter = null;
    @JsonProperty("sheepdog")
    public List<String> sheepdog = null;
    @JsonProperty("shiba")
    public List<Object> shiba = null;
    @JsonProperty("shihtzu")
    public List<Object> shihtzu = null;
    @JsonProperty("spaniel")
    public List<String> spaniel = null;
    @JsonProperty("springer")
    public List<String> springer = null;
    @JsonProperty("stbernard")
    public List<Object> stbernard = null;
    @JsonProperty("terrier")
    public List<String> terrier = null;
    @JsonProperty("vizsla")
    public List<Object> vizsla = null;
    @JsonProperty("waterdog")
    public List<String> waterdog = null;
    @JsonProperty("weimaraner")
    public List<Object> weimaraner = null;
    @JsonProperty("whippet")
    public List<Object> whippet = null;
    @JsonProperty("wolfhound")
    public List<String> wolfhound = null;
}