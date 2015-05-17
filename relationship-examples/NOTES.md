JPA Relationships and operations on entities
============================================


Standard Relationships
----------------------

The following describe the basic entity relationships, see more notes inside each project.

Key terms : *Single Value Association*, *Collection Value Association*.

1. **One to One unidirectional**, see project rel_one2one_uni

    Relationship of Employee and ParkingLot:
    
        @Entity
        public class Employee {
            @Id
            private long id;
        
            @Basic(optional = false)
            private String name;
        
            @OneToOne
            //@JoinColumn(name = "PARKING_ID")
            private ParkingLotEntity parkingLot;


        @Entity
        public class ParkingLotEntity {
            @Id private long id;
        
            @Basic(optional = false)
            private String name;

    Note: It's possible for two Employess to refer to the same ParkingLot

    
2. One to One bidirectional , see project rel_one2one_bi 

    Relationship of Employee and ParkingLot again.
    
    
3. Many to One , see project rel_many2one

    Relationship of Employee and Department.
    
    
4. One to Many unidirectional, see project rel_one2many_uni

    Relationship of Person and Phones
    
    
5. **One to Many bidirectional** , see project rel_one2many_bi

    Relationship of Employee and Department again.
    
        @Entity
        public class Department {        
            @Id
            private long id;
            ....
            @OneToMany(mappedBy="department")
            private List<Employee> employees = new ArrayList<>();

        @Entity
        public class Employee {        
            @Id
            private long id;
            ....
            /* OWNER SIDE*/
            @ManyToOne
            @JoinColumn(name = "DEPT_ID")
            private Department department;
    
    
    
6. Many to Many unidirectional, see project rel_many2many_uni

     RelationShip of Tasks and Employees.  (Task is owner).
    
7. **Many to Many bidirectional** , see project rel_many2many_bi

    Relationship of Employee and Project.
    
        @Entity
        public class Employee {
            @Id
            private long id;
            ...
            @ManyToMany
            @JoinTable(name = "EMPLOYEE_PROJECT", 
                joinColumns = @JoinColumn(name="EMPLOYEE_ID"), 
                inverseJoinColumns = @JoinColumn(name="PROJECT_ID"))
            private Set<Project> projects;
            
            
        @Entity
        public class Project {
            @Id
            private long id;
            ...
            @ManyToMany(mappedBy = "projects")
            private Set<Employee> employees;


Collection Mappings
-------------------

1. **Element collection example**, see project cm_element_collection 

    Collection Mapping of Employee to NickName(s) and VacationEntry(ies). 
    
        @Embeddable
        public class VacationEntry {
            @Temporal(TemporalType.DATE)
            private Date startDate;
            private int duration;
            
        @Entity
        public class Employee {
            @Id
            private long id;
               
            private String name;
        
            @ElementCollection
            @CollectionTable(name="CM_EC_EMP_VACATION",
                             joinColumns = @JoinColumn(name="EMP_ID"))
            @AttributeOverrides({ .. })
            private List<VacationEntry> vacationEntries  = new ArrayList<>();
        
            @ElementCollection
            @CollectionTable(name = "CM_EC_EMP_NNAMES", joinColumns = @JoinColumn(name = "EMP_ID"))
            private List<String> nickNames = new ArrayList<>();


    
2. List Ordering , see project  cm_list
    
    - Use of @OrderBy in Employees-Department relationship, Unresolved **problem**: cannot obtain sorted list of department employess.
    - Example of @OrderColumn  with PrintQueue PrintJob, same  **problem** cannot get them in proper order
    
3. **Map mapping keyed by basic type**, String in our case, see project cm_map_key_basic
    - Key is String under package jpa.relationship.mapuse.stringkey
    
        Person with phones mapped by category
         
        @Entity
        public class PersonStringPhoneType {
        
        @Id 
        private int id;
        
        private String name;
        
        @ElementCollection
        @CollectionTable(name = "PERSON_PHONE")
        @MapKeyColumn(name = "PHONE_TYPE")
        @Column(name = "PHONE_NUM")
        private Map<String, String> phoneNumbers = new HashMap<>();

4. **Map mapping keyed by enum**, see project cm_map_key_enum       
        
    - Key is Emum under package jpa.relationship.mapuse.emumkey
    
     Same as above using an Enum instead of a String.
        
        public enum PhoneType {
            HOME, MOBILE
        }
         
        @Entity
        public class PersonEnumPhoneType {
        
        @Id   
        private int id;
        
        private String name;
        
        @ElementCollection
        @CollectionTable(name = "EMP_PHONE")
        @MapKeyEnumerated(EnumType.STRING)
        @Column(name = "PHONE_NUM")
        private Map<PhoneType, String> phoneNumbers = new HashMap<>();
 
5. **Map used in One to Many relationship**, see project cm_map_one2many

        @Entity
        public class Employee {        
            @Id
            private long id;
            ...        
            @ManyToOne
            @JoinColumn(name = "DEPT_ID")
            private Department department;
            
            
        Entity
        public class Department {        
            @Id
            private long id;
            ...
            @OneToMany(mappedBy="department")
            @MapKeyColumn(name="CUB_ID", nullable = true)
            private Map<String, Employee> employeesByCubicle;

6. **Map used in Many to Many relationship**, see project cm_map_many2many


Weaknesses
----------

- Unresolved issues with @OrderBy
    
Future
------
- Explore @OrderBy when applied at @ElementCollection of basic type
